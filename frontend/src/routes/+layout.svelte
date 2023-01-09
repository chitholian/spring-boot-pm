<script>
    import '../app.css'
    import LeftMenu from "../components/LeftMenu.svelte";
    import {loaders} from "$lib/stores/loader.store.js";
    import axios from "axios";
    import {goto} from "$app/navigation";
    axios.interceptors.request.use(function (config) {
        // Do something before request is sent
        $loaders++;
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
                // let baseUrl = Config.vueUrl
                // if (!baseUrl || baseUrl === '') baseUrl = '/';
                // window.location.href = baseUrl
                goto("/")
            }
        } else {
            // console.log(e)
        }
        return Promise.reject(e)
    });
</script>
<div class="main-container">
    <LeftMenu/>
    <main>
        <slot/>
    </main>
</div>

<div class="page-loader" class:invisible={$loaders === 0}></div>
