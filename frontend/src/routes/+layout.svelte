<script>
    import '../app.css'
    import LeftMenu from "../lib/components/LeftMenu.svelte";
    import {loaders} from "$lib/stores/loader.store.js";
    import axios from "axios";
    import {onMount} from "svelte";
    import {jwt, user} from "$lib/stores/user.store.js";
    import {page} from "$app/stores";
    import authService from "$lib/services/auth.service.js";
    import {goto} from "$app/navigation";

    axios.interceptors.request.use(function (config) {
        // Do something before request is sent
        $loaders++;
        let wt = getToken()
        if (wt) config.headers.set('Authorization', 'Bearer ' + wt)
        return config;
    }, function (error) {
        // Do something with request error
        $loaders--;
        return Promise.reject(error);
    });

    axios.interceptors.response.use(function (response) {
        // Do something with response data
        $loaders--;
        return response;
    }, function (e) {
        // Do something with response error
        $loaders--;
        if (e.response) {
            if (e.response.status === 401 || e.response.status === 403) {
                $user = null;
                $jwt = null;
                if (!['/login'].includes($page.url.pathname)) goto('/login', {replaceState: true});
            }
        } else {
            // console.log(e)
        }
        return Promise.reject(e)
    });
    let getToken = () => $jwt;
    onMount(() => {
        authService.initialize()
    })
</script>
<div class="main-container">
    {#if $user}
        <LeftMenu/>
        <main>
            <slot/>
        </main>
    {:else if ['/login', '/register'].includes($page.url.pathname)}
        <slot/>
    {/if}
</div>

<div class="page-loader" class:invisible={$loaders === 0}></div>
