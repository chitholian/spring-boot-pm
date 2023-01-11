<script>
    import BasePage from "$lib/components/BasePage.svelte";
    import projectService from "$lib/services/project.service.js";
    import {extractErr} from "$lib/helpers.js";
    import {goto} from "$app/navigation";
    import {page} from "$app/stores";
    import {onMount} from "svelte";

    let form = {
        name: '',
        intro: '',
        description: '',
    }
    let errors = {}, error = '', loaders = 0;
    let project = null;

    function fetchProject() {
        loaders++
        projectService.fetchProject($page.params.id).then(({data}) => {
            if (data.status === 200) {
                project = data.data
                form.name = project.projectName
                form.intro = project.intro
                form.description = project.description
            }
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function submitForm() {
        loaders++
        projectService.editProject(project.id, form.name, form.intro, form.description).then(({data}) => {
            if (data.status === 200) {
                goto('/projects/' + project.id, {replaceState: true})
            }
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    onMount(() => {
        fetchProject()
    })
</script>

<BasePage title="Edit Project Info">
    {#if project}
        <form class="card max-w-[512px] m-auto mt-2" method="post" on:submit|preventDefault={submitForm}>
            <div class="font-bold pb-2">Edit Project Info</div>
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
                <button class="btn" type="submit" disabled={loaders > 0 || !form.name}>
                    Update
                </button>
            </div>
            {#if error}
                <div class="text-red-500 mt-2 text-center">{error}</div>
            {/if}
        </form>
    {/if}
</BasePage>
