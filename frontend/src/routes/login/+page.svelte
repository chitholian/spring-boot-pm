<div class="flex w-screen h-screen justify-center items-center" on:submit|preventDefault={submitForm}>
    <form method="post" class="login-form">
        <div class="text-center">
            <i class="p-5 fas fa-user text-5xl rounded-full ring-1 ring-blue-400 text-blue-400"></i>
        </div>
        <div class="text-input">
            <label for="username">Username</label>
            <input type="text" name="username" id="username" class="text-field w-full" bind:value={username}>
        </div>
        <div class="text-input mt-2">
            <label for="username">Password</label>
            <input type="password" name="password" id="password" class="text-field w-full" bind:value={password}>
        </div>
        <div class="flex items-center justify-between mt-2">
            <a href="/forgot-password" class="link">Forgot Password ?</a>
            <button type="submit" class="btn btn-primary" disabled={!username || !password}>
                Login <i class="fas fa-sign-in"></i>
            </button>
        </div>
    </form>
</div>
<script>
    let username = '', password = '';

    function submitForm() {
        alert('You have entered username: '+username+', password: '+password)
    }
</script>
