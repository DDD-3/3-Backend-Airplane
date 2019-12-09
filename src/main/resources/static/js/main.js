'use strict';

var joinPage = document.querySelector('#join-page');
var chatPage = document.querySelector('#chat-page');
var joinForm = document.querySelector('#joinForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;

var roomId = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    roomId = document.querySelector('#roomId').value.trim();
    username = document.querySelector('#name').value.trim();

    if(username) {
        joinPage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe(`/topic/room/${roomId}`, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/room/" + roomId + "/joinRoom",
        {},
        JSON.stringify(
            {
                type: 'JOIN',
                roomId: roomId,
                senderId: username
            }
        )
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            type: 'CHAT',
            roomId: roomId,
            senderId: username,
            content: messageInput.value
        };
        stompClient.send(`/app/room/${roomId}/sendMessage`, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.senderId + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.senderId + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.senderId[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.senderId);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.senderId);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSenderId) {
    var hash = 0;
    for (var i = 0; i < messageSenderId.length; i++) {
        hash = 31 * hash + messageSenderId.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

joinForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)