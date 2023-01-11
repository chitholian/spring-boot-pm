<BasePage title="Projects">
    <div class="card">
        <div class="font-bold pb-2">
            List of Projects
            <button class="btn" title="Print Report" on:click={printProjectReport}>
                <i class="fas fa-print"></i>
            </button>
            <div class="float-right">
                <a role="button" class="btn" href="/projects/create">
                    <i class="fas fa-plus"></i> Create New
                </a>
            </div>
        </div>
        <table class="w-full bordered striped">
            <thead>
            <tr>
                <th>Sl.</th>
                <th>ID</th>
                <th>Title</th>
                <th>Contributors</th>
                <th>Status</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {#each projects as item,i(item.id)}
                <tr class="text-center">
                    <td>{i + 1}</td>
                    <td>{item.id}</td>
                    <td class="text-left">{item.projectName}</td>
                    <td>
                        <a href="/projects/{item.id}/contributors" title="Show Contributors">
                            {item.memberCount + 1}
                        </a>
                    </td>
                    <td class="text-left">
                        {#if item.statusId === 0}
                            <i class="text-red-500 fas fa-clock"></i>
                        {:else if item.statusId === 1}
                            <i class="text-blue-500 fas fa-dashboard"></i>
                        {:else if item.statusId === 3}
                            <i class="text-green-500 fas fa-check"></i>
                        {/if}
                        {item.statusName}
                    </td>
                    <td>{item.startDate ?? ''}</td>
                    <td>{item.endDate ?? ''}</td>
                    <td>
                        <a href="/projects/{item.id}" title="Show Details">
                            <i class="fas fa-external-link"></i>
                        </a>
                        {#if item.ownerId === 1 && item.statusId === 0}
                            <button class="fas fa-play ml-2 text-green-500 cursor-pointer" on:click={() => startProject(item)}
                               title="Start Project"></button>
                        {:else}
                            <button class="fas invisible fa-play ml-2"></button>
                        {/if}
                    </td>
                </tr>
            {/each}
            {#if !projects.length}
                <tr>
                    <td colspan="8" class="p-2 text-black bg-yellow-100 text-center">No Data Available</td>
                </tr>
            {/if}
            </tbody>
        </table>
        {#if error}
            <div class="text-red-500 m-1 text-center">{error}</div>
        {/if}
    </div>
</BasePage>
<script>
    import {onMount} from "svelte";
    import projectService from "$lib/services/project.service.js";
    import {extractErr} from "$lib/helpers.js";
    import BasePage from "$lib/components/BasePage.svelte";
    import {setMenu} from "$lib/stores/menu.store.js";

    onMount(() => {
        setMenu('projects')
    })

    let errors = {}, error = '', loaders = 0;
    let projects = []

    function fetchProjects() {
        loaders++
        projectService.fetchProjects().then(({data}) => {
            if (data.status === 200) projects = data.data
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function startProject(item) {
        if(!confirm("Are you sure to START this project ?")) return;
        loaders++
        projectService.startProject(item.id).then(({data}) => {
            fetchProjects()
            alert('Project started successfully')
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }
    function printProjectReport() {

    }

    onMount(() => {
        fetchProjects()
    })
</script>
