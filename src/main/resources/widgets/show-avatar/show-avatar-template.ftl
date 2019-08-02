<#assign username = Session.currentUser.username>
<div style="display: flex; align-items: center">
    <span>${username}</span>
    <img src="http://localhost:8081/avatarPlugin/api/avatars/image/${username}" />
</div>
