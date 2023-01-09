<BasePage title="Project Details">
    {#if project}
        <div class="card">
            <div class="font-bold pb-1">
                Project Details :: {project.projectName}
                <button class="btn rounded-md px-1" title="Edit Project Info">
                    <i class="fas fa-edit"></i>
                </button>
                <div class="float-right">
                    <button class="btn rounded-md px-1" title="Add Member">
                        <i class="fas fa-user-plus"></i>
                    </button>
                </div>
            </div>
            <div class="text-sm italic">{project.intro}</div>
        </div>
        <div class="flex justify-center text-center">
            <div class="basis-1/3 m-1 card">
                Owner <br>
                <strong>{project.ownerName}</strong>
            </div>
            <div class="basis-1/3 m-1 card">
                Contributors <br>
                <a href="/projects/{project.id}/contributors" title="Show Contributors">
                    <strong class="text-2xl">{project.memberCount + 1}</strong>
                </a>
            </div>
            <div class="basis-1/3 m-1 card">
                Status <br>
                <strong>
                    {project.statusName}
                    {#if project.endDate}
                        :: {project.endDate}
                    {:else if project.startDate}
                        :: {project.startDate}
                    {/if}
                </strong>
            </div>
        </div>
        {#if project.statusId !== 3}
            <div class="mt-1 card">
                {#if project.statusId === 0}
                    <button class="btn rounded-md px-2 border-0 bg-green-500 text-white"
                        on:click={startProject}>
                        <i class="fas fa-play-circle"></i> Start Project
                    </button>
                {:else if project.statusId === 1}
                    <button class="btn rounded-md px-2 border-0 bg-red-500 text-white"
                            on:click={endProject}>
                        <i class="fas fa-stop-circle"></i> End Project
                    </button>
                {/if}
            </div>
        {/if}
    {:else if loaders > 0}
        <div class="text-center btn">Loading...</div>
    {/if}
    {#if error}
        <div class="text-red-500 m-1 text-center">{error}</div>
    {/if}
</BasePage>
<script>
    import {onMount} from "svelte";
    import projectService from "$lib/services/project.service.js";
    import {extractErr} from "$lib/helpers.js";
    import {setMenu} from "$lib/stores/menu.store.js";
    import BasePage from "../../../components/BasePage.svelte";
    import {page} from "$app/stores";

    onMount(() => {
        setMenu('projects')
    })

    let errors = {}, error = '', loaders = 0;
    let project = null;

    function fetchProject() {
        loaders++
        projectService.fetchProject($page.params.id).then(({data}) => {
            if (data.status === 200) project = data.data
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function startProject() {
        loaders++
        projectService.startProject(project.id).then(({data}) => {
            fetchProject()
            alert('Project started successfully')
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function endProject(item) {
        loaders++
        projectService.endProject(project.id).then(({data}) => {
            fetchProject()
            alert('Project ended successfully')
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    onMount(() => {
        fetchProject()
    })
</script>
