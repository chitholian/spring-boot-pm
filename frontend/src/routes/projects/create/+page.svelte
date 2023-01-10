<script>
    import BasePage from "../../../components/BasePage.svelte";
    import projectService from "$lib/services/project.service.js";
    import {extractErr} from "$lib/helpers.js";
    import {goto} from "$app/navigation";

    let form = {
        name: '',
        intro: '',
        description: '',
        startNow: false,
    }
    let errors = {}, error = '', loaders = 0;

    function submitForm() {
        loaders++
        projectService.createProject(form.name, form.intro, form.description, form.startNow).then(({data}) => {
            if (data.status === 200) {
                goto('/projects/' + data.data.id)
            }
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }
</script>

<BasePage title="Create Project">
    <form class="card max-w-[512px] m-auto mt-2" method="post" on:submit|preventDefault={submitForm}>
        <div class="font-bold pb-2">Create New Project</div>
        <div class="text-input mt-2">
            <label for="name">Title</label>
            <input type="text" name="name" id="name" class="text-field w-full" bind:value={form.name} required>
            {#if errors.name}
                <div class="form-err">{errors.name}</div>
            {/if}
        </div>
        <div class="text-input mt-2">
            <label for="intro">Intro</label>
            <input type="text" name="intro" id="intro" class="text-field w-full" bind:value={form.intro}>
        </div>
        <div class="text-input mt-2">
            <label for="description">Project Description</label>
            <textarea name="description" rows="5" id="description" class="text-field w-full"
                      bind:value={form.description}></textarea>
        </div>
        <div class="mt-2 flex justify-between items-center">
            <div class="check-input">
                <input id="startNow" name="startNow" type="checkbox" class="cb" bind:checked={form.startNow}>
                <label for="startNow">Start Immediately</label>
            </div>
            <button class="btn" type="submit" disabled={loaders > 0 || !form.name}>
                Submit
            </button>
        </div>
        {#if error}
            <div class="text-red-500 mt-2 text-center">{error}</div>
        {/if}
    </form>
</BasePage>
