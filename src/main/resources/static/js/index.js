// 告知後端用戶上線
const TYPE_COMMAND_ONLINE = "userOnline";
// 打電話請求
const TYPE_COMMAND_CALL = "call";


// 刷新當前線上用戶列表
const TYPE_COMMAND_USER_LIST = "userList";
// 後端發來消息(顯示至畫面上)
const TYPE_COMMAND_DIALOGUE = "dialogue";
// 已開啟攝像頭
const TYPE_COMMAND_READY = "ready";
// 接收到來自後端的offer
const TYPE_COMMAND_OFFER = "offer";
// 接收到來自後端的answer
const TYPE_COMMAND_ANSWER = "answer";
// 接收到來自後端的candidate
const TYPE_COMMAND_CANDIDATE = "candidate";


let iceServers = {
    "iceServers": [
        {"url": "stun:stun.services.mozilla.com"},
        {"url": "stun:stun.l.google.com:19302"}
    ]
};
const mediaConstraints = {
    video: {width: 500, height: 500},
    audio: false // true,//由于没有麦克风，所有如果请求音频，会报错，不过不会影响视频流播放
};


const offerOptions = {
    iceRestart: true,
    offerToReceiveAudio: false, //true,由于没有麦克风，所有如果请求音频，会报错，不过不会影响视频流播放
    offerToReceiveVideo: true
};

let localMediaStream;

let rtcPeerConnection;

//写在这里不行，得写到promise的then里面才起作用，暂不知为何
//const localVideo = document.getElementById("localVideo");
//const remoteVideo = document.getElementById("remoteVideo");

let websocket;
// let userId = randomName();
let roomId;
let log;

// 對方的ID
let userId;

function whoami() {
    get("/api/pub/whoami")
        .then((data) => {
            document.getElementById("showUserId").innerText = data.data;
        }).catch((error) => {
        log(error)
    })
}
async function mySubmit() {
    document.getElementById("login").submit();
    console.log(a);
    // form.submit();
    // document.getElementById("login").onsubmit
    // console.log("登入成功")
    // log("登入成功")
}


function connect() {
    //初始化websocket连接
    get("/api/pub/getWebSocketUrl")
        .then((data) => {
            // if (!websocket) {
            //     websocket = new WebSocket(data.url);
            //     log("websocket连接成功")
            // }
            websocket.close();
            websocket = new WebSocket(data.url);
            log("websocket连接成功")
            websocket.onopen = () => {
                websocket.send(JSON.stringify({command: TYPE_COMMAND_USER_LIST}))
            };
            websocket.onclose = () => {
                log("Connection closed.");
            };
            websocket.onerror = () => {
                log("websocket error");
            };
            websocket.onmessage = handleMessage;

        })
        .catch((error) => {
            log(error);
        });
}

function logout() {
    document.getElementById("showUserId").innerText = "未登入"
    get("/api/pub/logout")
        .then((data) => {
            log(data)
        }).catch((error) => {
        log(error)
    })
}

function call() {
    // 給vip打電話
    websocket.send(JSON.stringify({command: TYPE_COMMAND_DIALOGUE, userName: "vip"}))
}

function updateUsers() {
    websocket.send(JSON.stringify({command: TYPE_COMMAND_USER_LIST}))
}

window.onload = () => {

    //初始化websocket连接
    get("/api/pub/getWebSocketUrl")
        .then((data) => {
            if (!websocket) {
                websocket = new WebSocket(data.url);
                log("websocket连接成功")
            }
            websocket.onopen = () => {
                websocket.send(JSON.stringify({command: TYPE_COMMAND_USER_LIST}))
            };
            websocket.onclose = () => {
                log("Connection closed.");
            };
            websocket.onerror = () => {
                log("websocket error");
            };
            websocket.onmessage = handleMessage;

        })
        .catch((error) => {
            log(error);
        });


    //初始化log
    log = (message) => {
        let log = document.getElementById("logContent");
        let oneLog = document.createElement("span");
        oneLog.innerText = message;
        let br = document.createElement("br");
        log.append(oneLog, br);
    };

    //设定随机名称
    document.getElementById("showUserId").innerText = "未登入";

    //初始化各种按钮
    // document.getElementById("enterRoom").onclick = () => {
    //     roomId = document.getElementById("roomId").value;
    //     websocket.send(JSON.stringify({command: TYPE_COMMAND_ONLINE, userId: "", roomId: roomId}));
    //     //不用向服务器请求房间列表，在服务器的创建房间函数中，向每个终端发送TYPE_COMMAND_ROOM_LIST事件
    //     //websocket.send(JSON.stringify({command: TYPE_COMMAND_ROOM_LIST}));
    // };

    document.getElementById("sendMessage").onclick = () => {
        let textMessage = document.getElementById("textMessage").value;
        websocket.send(JSON.stringify({
            command: TYPE_COMMAND_DIALOGUE,
            userId: "",
            roomId: roomId,
            message: textMessage
        }));
    };

    document.getElementById("clearLog").onclick = () => {
        let logContentDiv = document.getElementById("logContent");
        while (logContentDiv.hasChildNodes()) {
            logContentDiv.removeChild(logContentDiv.firstChild);
        }
    };

};


const handleMessage = (event) => {
    log(event.data);
    let data = JSON.parse(event.data);
    switch (data.command) {
        // 刷新當前線上用戶列表
        case TYPE_COMMAND_USER_LIST:
            let userList = document.getElementById("userList");
            while (userList.hasChildNodes()) {
                userList.removeChild(userList.firstChild);
            }

            JSON.parse(data.message).forEach((userName) => {
                let item = document.createElement("div");
                let label = document.createElement("h3");
                label.innerText = userName;
                let btn1 = document.createElement("button");
                btn1.innerText = "發消息"
                let btn2 = document.createElement("button");
                btn2.innerText = "打電話"
                btn1.onclick = () => websocket.send(JSON.stringify({
                    command: TYPE_COMMAND_DIALOGUE,
                    userName: userName
                }))
                btn2.onclick = () => websocket.send(
                    JSON.stringify({
                        command: TYPE_COMMAND_READY,
                        userName: userName
                    }))
                item.append(label, btn1, btn2);
                userList.append(item);

            });


            break;
        // 後端發來消息(顯示至畫面上)
        case TYPE_COMMAND_DIALOGUE:
            break;
        // 對方已開啟攝像頭
        case TYPE_COMMAND_READY:

            openLocalMedia()
                .then(async () => {
                    log("打開本地攝像頭成功")

                    this.userId = data.userName;

                    rtcPeerConnection = new RTCPeerConnection(this.iceServers);

                    rtcPeerConnection.onicecandidate = onIceCandidate;

                    rtcPeerConnection.ontrack = onTrack;

                    for (const track of localMediaStream.getTracks()) {
                        rtcPeerConnection.addTrack(track, localMediaStream);
                    }

                    // 生成 offer
                    const offer = await rtcPeerConnection.createOffer();
                    // 設置本地 offer
                    await rtcPeerConnection.setLocalDescription(offer);
                    // 發送 offer
                    log("this.userId = " + this.userId);
                    websocket.send(JSON.stringify({
                            command: TYPE_COMMAND_OFFER,
                            userName: this.userId,
                            message: {
                                sdp: offer,
                            }
                        })
                    );

                })
                .catch(() => log("打開本地攝像頭失敗"));
            break;
        case TYPE_COMMAND_OFFER:
            openLocalMedia()
                .then(async () => {
                    rtcPeerConnection = new RTCPeerConnection(this.iceServers);

                    rtcPeerConnection.onicecandidate = onIceCandidate;

                    rtcPeerConnection.ontrack = onTrack;

                    for (const track of localMediaStream.getTracks()) {
                        rtcPeerConnection.addTrack(track, localMediaStream);
                    }

                    //原因：后端接口返回的数据换行采用了\r\n方式，使得json文本无法解析带换行符的内容
                    //解决方法：将Json字符串中所有的\r\n转成\\r\\n
                    let sdpMessageOffer = data.message;
                    sdpMessageOffer.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n");
                    log(sdpMessageOffer);
                    let sdpOffer = JSON.parse(sdpMessageOffer).sdp;
                    await rtcPeerConnection.setRemoteDescription(new RTCSessionDescription(sdpOffer))

                    // 生成 answer
                    const answer = await rtcPeerConnection.createAnswer();
                    // 設置本地 answer
                    await rtcPeerConnection.setLocalDescription(answer);

                })
                .catch((e) => {
                    log(e)
                    log("打開本地攝像頭失敗")
                });

            break;
        case TYPE_COMMAND_ANSWER:
            //原因：后端接口返回的数据换行采用了\r\n方式，使得json文本无法解析带换行符的内容
            //解决方法：将Json字符串中所有的\r\n转成\\r\\n
            let sdpMessageAnswer = data.message;
            sdpMessageAnswer.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n");
            log(sdpMessageAnswer);
            let sdpAnswer = JSON.parse(sdpMessageAnswer).sdp;
            rtcPeerConnection.setRemoteDescription(new RTCSessionDescription(sdpAnswer))
                .then(
                    log("setRemoteDescription 完毕")
                );
            break;

            // sdpMid: event.candidate.sdpMid,
            // sdpMLineIndex: event.candidate.sdpMLineIndex,
            // candidate: event.candidate.candidate

        case TYPE_COMMAND_CANDIDATE:
            let candidateMessage = data.message;
            log(candidateMessage);
            let candidate = JSON.parse(candidateMessage).candidate;
            let rtcIceCandidate = new RTCIceCandidate({
                sdpMid: candidate.sdpMid,
                sdpMLineIndex: candidate.sdpMLineIndex,
                candidate: candidate.candidate
            });
            rtcPeerConnection.addIceCandidate(rtcIceCandidate)
                .then(
                    log("addIceCandidate 完毕")
                );
            break;
    }
}

// const handleMessage = (event) => {
//     log(event);
//     log(event.data);
//     let message = JSON.parse(event.data);
//     switch (message.command) {
//         case TYPE_COMMAND_ROOM_ENTER:
//             if (message.message === "joined") {
//                 log("加入房间：" + message.roomId + "成功");
//                 roomId = message.roomId;
//                 openLocalMedia()
//                     .then(() => {
//                         log("打开本地音视频设备成功");
//                         websocket.send(JSON.stringify({command: TYPE_COMMAND_READY, userId: userId, roomId: roomId}));
//                     })
//                     .catch(() => {
//                         log("打开本地音视频设备失败");
//                     })
//             } else {
//                 log("创建房间：" + message.roomId + "成功");
//                 caller = true;
//                 openLocalMedia()
//                     .then(() => log("打开本地音视频设备成功"))
//                     .catch(() => log("打开本地音视频设备失败"));
//             }
//
//             break;
//         case TYPE_COMMAND_ROOM_LIST:
//             let roomList = document.getElementById("roomList");
//             //这个方法会少删子节点，不知为何，用另一个方法
//             /*roomList.childNodes.forEach((node) =>{
//                 roomList.removeChild(node);
//             });*/
//             //当div下还存在子节点时 循环继续
//             while (roomList.hasChildNodes()) {
//                 roomList.removeChild(roomList.firstChild);
//             }
//             JSON.parse(message.message).forEach((roomId) => {
//                 //大厅默认已经加入，不把大厅展示在房间列表
//                 if( roomId !== "lobby"){
//                     let item = document.createElement("div");
//                     let label = document.createElement("label");
//                     label.setAttribute("for", roomId);
//                     label.innerText = "房间号：";
//                     let span = document.createElement("span");
//                     span.innerText = roomId;
//                     let button = document.createElement("button");
//                     button.innerText = "加入房间";
//                     button.onclick = () => websocket.send(JSON.stringify({
//                         command: TYPE_COMMAND_ROOM_ENTER,
//                         userId: userId,
//                         roomId: roomId
//                     }));
//                     item.append(label, span, button);
//                     roomList.append(item);
//                 }
//             });
//             break;
//         case TYPE_COMMAND_DIALOGUE:
//             let dialogue = document.createElement("p").innerText = message.userId + ":" + message.message;
//             let br = document.createElement("br");
//             document.getElementById("dialogueList").append(dialogue, br);
//             break;
//         case TYPE_COMMAND_READY:
//             if (caller) {
//                 //初始化一个webrtc端点
//                 rtcPeerConnection = new RTCPeerConnection(iceServers);
//                 //添加事件监听函数
//                 rtcPeerConnection.onicecandidate = onIceCandidate;
//                 rtcPeerConnection.ontrack = onTrack;
//
//                 //addStream相关的方法，已过时
//                 //rtcPeerConnection.addTrack(localMediaStream);
//                 for( const track of localMediaStream.getTracks()){
//                     rtcPeerConnection.addTrack(track, localMediaStream);
//                 }
//                 rtcPeerConnection.createOffer(offerOptions)
//                     .then(
//                         setLocalAndOffer
//                     )
//                     .catch(() => {
//                             log("setLocalAndOffer error:")
//                         }
//                     );
//             }
//             break;
//         case TYPE_COMMAND_OFFER:
//             if (!caller) {
//                 //初始化一个webrtc端点
//                 rtcPeerConnection = new RTCPeerConnection(iceServers);
//                 //添加事件监听函数
//                 rtcPeerConnection.onicecandidate = onIceCandidate;
//
//                 rtcPeerConnection.ontrack = onTrack;
//
//                 //rtcPeerConnection.addTrack(localMediaStream);
//                 for (const track of localMediaStream.getTracks()) {
//                     rtcPeerConnection.addTrack(track, localMediaStream);
//                 }
//                 //原因：后端接口返回的数据换行采用了\r\n方式，使得json文本无法解析带换行符的内容
//                 //解决方法：将Json字符串中所有的\r\n转成\\r\\n
//                 let sdpMessage = message.message;
//                 sdpMessage.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n");
//                 log(sdpMessage);
//                 let sdp = JSON.parse(sdpMessage).sdp;
//                 rtcPeerConnection.setRemoteDescription(new RTCSessionDescription(sdp))
//                     .then(
//                         log("setRemoteDescription 完毕")
//                     );
//                 rtcPeerConnection.createAnswer(offerOptions)
//                     .then(
//                         setLocalAndAnswer
//                     )
//                     .catch(() => {
//                             log("setLocalAndAnswer error");
//                         }
//                     );
//             }
//             break;
//         case TYPE_COMMAND_ANSWER:
//             //原因：后端接口返回的数据换行采用了\r\n方式，使得json文本无法解析带换行符的内容
//             //解决方法：将Json字符串中所有的\r\n转成\\r\\n
//             let sdpMessage = message.message;
//             sdpMessage.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n");
//             log(sdpMessage);
//             let sdp = JSON.parse(sdpMessage).sdp;
//             rtcPeerConnection.setRemoteDescription(new RTCSessionDescription(sdp))
//                 .then(
//                     log("setRemoteDescription 完毕")
//                 );
//             break;
//         case TYPE_COMMAND_CANDIDATE:
//             let candidateMessage = message.message;
//             log(candidateMessage);
//             let candidate = JSON.parse(candidateMessage).candidate;
//             let rtcIceCandidate = new RTCIceCandidate({
//                 sdpMid: candidate.sdpMid,
//                 sdpMLineIndex: candidate.label,
//                 candidate: candidate.candidate
//             });
//             rtcPeerConnection.addIceCandidate(rtcIceCandidate)
//                 .then(
//                     log("addIceCandidate 完毕")
//                     );
//             break;
//     }
//
//
// };
//
//打开本地音视频,用promise这样在打开视频成功后，再进行下一步操作
const openLocalMedia = () => {
    return new Promise((resolve, reject) => {
        navigator.mediaDevices.getUserMedia(mediaConstraints)
            .then((stream) => {
                //make stream available to browser console(设置不设置都没问题)
                //window.stream = mediaStream;
                //localVideo.srcObject = mediaStream;
                localMediaStream = stream;
                let localVideo = document.getElementById("localVideo");
                localVideo.srcObject = localMediaStream;
                localVideo.play();
            })
            .then(() => resolve())
            .catch(() => reject());
    });

};

onTrack = async (event) => {
    let remoteMediaStream = event.streams[0];
    let remoteVideo = document.getElementById("remoteVideo");
    remoteVideo.srcObject = remoteMediaStream;
    remoteVideo.play();
};

const onIceCandidate = (event) => {
    if (event.candidate) {
        log("sending ice candidate");
        websocket.send(JSON.stringify({
            command: TYPE_COMMAND_CANDIDATE,
            userName: this.userId,
            message: {
                candidate: {
                    sdpMid: event.candidate.sdpMid,
                    sdpMLineIndex: event.candidate.sdpMLineIndex,
                    candidate: event.candidate.candidate
                }
            }
        }));
    }
};


// const setLocalAndOffer = (sessionDescription) => {
//     rtcPeerConnection.setLocalDescription(sessionDescription)
//         .then(
//             log("setLocalDescription 完毕")
//         );
//     websocket.send(
//         JSON.stringify({
//             command: TYPE_COMMAND_OFFER,
//             userId: userId,
//             roomId: roomId,
//             message: {
//                 sdp: sessionDescription,
//             }
//         })
//     );
// };
//
//
// const setLocalAndAnswer = (sessionDescription) => {
//     rtcPeerConnection.setLocalDescription(sessionDescription)
//         .then(
//             log("setLocalDescription 完毕")
//         );
//     websocket.send(
//         JSON.stringify({
//             command: TYPE_COMMAND_ANSWER,
//             userId: userId,
//             roomId: roomId,
//             message: {
//                 sdp: sessionDescription,
//             }
//         })
//     );
// };
//
