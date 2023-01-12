<script>
    import BasePage from "../lib/components/BasePage.svelte";
    import {onMount} from "svelte";
    import {setMenu} from "$lib/stores/menu.store.js";
    import {extractErr} from "$lib/helpers.js";
    import authService from "$lib/services/auth.service.js";
    import {user} from "$lib/stores/user.store.js";

    let errors = {}, error = '', loaders = 0;

    function loadData() {
        loaders++
        authService.getDashboardData().then(({data}) => {
            info = data.data
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    onMount(() => {
        setMenu('home')
        loadData()
    })

    let info = null;

</script>
<BasePage title="Dashboard">
    {#if info}
        <div class="md:flex justify-center text-center">
            <div class="basis-1/3 m-1 card">
                <i class="fas fa-user stats-icon bg-red-100"></i> <br>
                <strong>My Profile</strong>
                <div class="p-2 flex justify-center">
                    <table class="text-left">
                        <tbody>
                        <tr>
                            <td>Name</td>
                            <td>:</td>
                            <td>{$user.name}</td>
                        </tr>
                        <tr>
                            <td>Email</td>
                            <td>:</td>
                            <td>{$user.email}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="basis-1/3 m-1 card">
                <i class="fas fa-code stats-icon bg-green-100"></i> <br>
                <a href="/projects"><strong class="text-5xl">{info.createdItems}</strong></a>
                <br> Projects Created
            </div>
            <div class="basis-1/3 m-1 card">
                <i class="fas fa-code-branch stats-icon bg-blue-100"></i> <br>
                <a href="/projects"><strong class="text-blue-900 text-5xl">{info.contributedItems}</strong></a>
                <br> Contributing Projects
            </div>
        </div>
    {:else}
        <div class="text-center p-4">Loading...</div>
    {/if}
    {#if error}
        <div class="text-red-500 m-2 text-center">{error}</div>
    {/if}
</BasePage>
