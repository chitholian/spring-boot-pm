<script>
    import BasePage from "../../lib/components/BasePage.svelte";
    import {onMount} from "svelte";
    import {setMenu} from "$lib/stores/menu.store.js";
    onMount(() => {
        setMenu('about')
    })
</script>

<BasePage title="About">
    <div class="flex w-full h-full justify-center items-center">
        About This Project Goes Here
    </div>
</BasePage>
