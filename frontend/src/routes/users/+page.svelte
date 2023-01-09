<script>
    import BasePage from "../../components/BasePage.svelte";
    import {onMount} from "svelte";
    import {setMenu} from "$lib/stores/menu.store.js";

    onMount(() => {
        setMenu('users')
    })
</script>

<BasePage title="List of Users">
    <div class="flex w-full h-full justify-center items-center">
        User List Goes Here
    </div>
</BasePage>
