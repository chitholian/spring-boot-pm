<div class="flex w-screen h-screen justify-center items-center" on:submit|preventDefault={submitForm}>
    <form method="post" class="login-form">
        <div class="text-center">
            <i class="p-5 fas fa-user text-5xl rounded-full ring-1 ring-blue-400 text-blue-400"></i>
            <br>
            <div class="text-xl mt-2">User Registration</div>
        </div>
        {#if error}
            <div class="text-red-500 m-1 text-center">{error}</div>
        {/if}
        <div class="text-input">
            <label for="name">Full Name</label>
            <input type="text" name="name" id="name" class="text-field w-full" bind:value={form.name} required>
            {#if errors.name}
                <div class="form-err">{errors.name}</div>
            {/if}
        </div>
        <div class="text-input mt-2">
            <label for="email">Email</label>
            <input type="email" name="email" id="email" class="text-field w-full" bind:value={form.email} required>
            {#if errors.email}
                <div class="form-err">{errors.email}</div>
            {/if}
        </div>
        <div class="text-input mt-2">
            <label for="name">Password</label>
            <input type="password" name="password" id="password" class="text-field w-full" bind:value={form.password}
                   required>
        </div>
        <div class="text-input mt-2">
            <label for="password2">Confirm Password</label>
            <input type="password" name="password2" id="password2" class="text-field w-full" bind:value={form.password2}
                   required>
            {#if errors.password2}
                <div class="form-err">{errors.password2}</div>
            {/if}
        </div>
        <div class="flex items-center justify-between mt-2">
            <span>Have an account ? <a href="/login">Login.</a></span>
            <button type="submit" class="btn btn-primary"
                    disabled={loaders > 0 || !form.name || !form.password || errors.password2}>
                Submit
                {#if loaders > 0}
                    <i class="fas fa-spin fa-spinner"></i>
                {/if}
            </button>
        </div>
    </form>
</div>
<script>
    import authService from "$lib/services/auth.service.js";
    import {goto} from "$app/navigation";
    import {extractErr} from "$lib/helpers.js";

    let form = {
        name: '',
        email: '',
        password: '',
        password2: '',
    };

    let errors = {};
    let error = '', loaders = 0;

    $: if(form.password2 && form.password2 !== form.password){
        errors = {...errors, password2: 'Passwords do not match'}
    } else {
        errors = {...errors, password2: ''}
    }

    function submitForm() {
        loaders++
        authService.register(form.name, form.email, form.password).then(({data}) => {
            if (data.status === 200) {
                goto('/login', {replaceState: true})
            }
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }
</script>
