<script>
    import BasePage from "../../components/BasePage.svelte";
    import {onMount} from "svelte";
    import {setMenu} from "$lib/stores/menu.store.js";

    onMount(() => {
        setMenu('contact')
    })
</script>

<BasePage title="Contact">
    <div class="flex w-full h-full justify-center items-center">
        Contact Info Goes Here
    </div>
</BasePage>
