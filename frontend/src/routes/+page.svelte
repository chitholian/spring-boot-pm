<script>
    import BasePage from "../components/BasePage.svelte";
    import {onMount} from "svelte";
    import {setMenu} from "$lib/stores/menu.store.js";

    onMount(() => {
        setMenu('home')
    })
</script>
<BasePage>

</BasePage>
