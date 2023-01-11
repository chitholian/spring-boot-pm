<BasePage title="Project Details">
    {#if project}
        <div class="card">
            <div class="font-bold pb-1">
                Project Details :: {project.projectName}
                <a role="button" class="btn rounded-md px-1" title="Edit Project Info"
                   href="/projects/{project.id}/edit">
                    <i class="fas fa-edit"></i>
                </a>
                <div class="float-right">
                    <a href="/projects/{project.id}/contributors" role="button" class="btn rounded-md px-1"
                       title="Add Member"><i class="fas fa-user-plus"></i>
                    </a>
                </div>
            </div>
            <div class="text-sm italic">{project.intro}</div>
        </div>
        <div class="flex justify-center text-center">
            <div class="basis-1/3 m-1 card">
                <i class="fas fa-user stats-icon bg-blue-100"></i> <br>
                Owner <br>
                <strong>{project.ownerName}</strong>
            </div>
            <div class="basis-1/3 m-1 card">
                <i class="fas fa-users stats-icon bg-green-100"></i> <br>
                Contributors <br>
                <a href="/projects/{project.id}/contributors" title="Show Contributors">
                    <strong class="text-2xl">{project.memberCount + 1}</strong>
                </a>
            </div>
            <div class="basis-1/3 m-1 card">
                <i class="fas fa-bars-progress stats-icon bg-red-100"></i> <br>
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
        <div class="card mt-1 mb-2">
            Created At: <em>{project.createdAt}</em>
            {#if project.description}
                <div class="font-bold pb-2">Description</div>
                {project.description}
            {/if}
        </div>
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
            <button class="btn rounded-md px-2 border-0 bg-red-500 text-white float-right"
                    on:click={deleteProject}>
                <i class="fas fa-trash"></i> Delete This Project
            </button>
            <div class="clear-both"></div>
        </div>
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
    import BasePage from "../../../lib/components/BasePage.svelte";
    import {page} from "$app/stores";
    import {goto} from "$app/navigation";

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
        if (!confirm("Are you sure to START this project ?")) return;
        loaders++
        projectService.startProject(project.id).then(({data}) => {
            fetchProject()
            alert('Project started successfully')
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function endProject() {
        if (!confirm("Are you sure to END this project ?")) return;
        loaders++
        projectService.endProject(project.id).then(({data}) => {
            fetchProject()
            alert('Project ended successfully')
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function deleteProject() {
        let title = prompt('Enter exact title of the project to delete it permanently:')
        if (title === undefined || title === null) return;
        if (title !== project.projectName) {
            alert('Incorrect title, deletion failed.');
            return;
        }
        loaders++
        projectService.deleteProject(project.id).then(({data}) => {
            alert('Project deleted successfully')
            goto('/projects', {replaceState: true})
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    onMount(() => {
        fetchProject()
    })
</script>
