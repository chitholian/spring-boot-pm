<BasePage title="User Login">
    <div class="flex w-screen h-screen justify-center items-center" on:submit|preventDefault={submitForm}>
        <form method="post" class="login-form">
            <div class="text-center">
                <i class="fas fa-user stats-icon bg-blue-100"></i>
                <br>
                <div class="text-xl mt-2">User Login</div>
            </div>
            {#if error}
                <div class="text-red-500 m-1 text-center">{error}</div>
            {/if}
            <div class="text-input">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" class="text-field w-full" bind:value={form.username}>
            </div>
            <div class="text-input mt-2">
                <label for="username">Password</label>
                <input type="password" name="password" id="password" class="text-field w-full" bind:value={form.password}>
            </div>
            <div class="flex items-center justify-between mt-2">
                <a href="/forgot-password" class="invisible">Forgot Password ?</a>
                <button type="submit" class="btn btn-primary" disabled={loaders > 0 || !form.username || !form.password}>
                    Login <i class="fas fa-sign-in"></i>
                </button>
            </div>
            <div class="mt-2">
                Don't have an account ? <a href="/register">Create account.</a>
            </div>
        </form>
    </div>
</BasePage>
<script>
    import authService from "$lib/services/auth.service.js";
    import {goto} from "$app/navigation";
    import {extractErr} from "$lib/helpers.js";
    import BasePage from "../../lib/components/BasePage.svelte";

    let form = {
        username: '',
        password: '',
    };

    let error = '', errors = {}, loaders = 0;

    function submitForm() {
        authService.login(form.username, form.password).then(({data}) => {
            if (data.status === 200) {
                authService.storeAuthInfo(data.data.user, data.data.jwt);
                goto('/', {replaceState: true})
            }
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }
</script>
