<BasePage title="Project Contributors">
    {#if project}
        <div class="card">
            <div class="pb-1 font-bold">
                Project Contributors :: {project.projectName}
                <div class="float-right">
                    Owner: {project.ownerName}
                </div>
            </div>
            <table class="w-full bordered striped">
                <thead>
                <tr>
                    <th>Sl.</th>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {#each items as item,i(item.id)}
                    <tr class="text-center">
                        <td>{i + 1}</td>
                        <td>{item.id}</td>
                        <td class="text-left">{item.name}</td>
                        <td class="text-left">{item.email}</td>
                        <td>
                            {#if project && project.ownerId === 1}
                                <i class="fas fa-minus-circle text-red-500 cursor-pointer"
                                   on:click={() => removeUser(item)}
                                   title="Remove Member"></i>
                            {/if}
                        </td>
                    </tr>
                {/each}
                {#if !items.length}
                    <tr>
                        <td colspan="8" class="p-2 text-black bg-yellow-200 text-center">No Data Available</td>
                    </tr>
                {/if}
                </tbody>
            </table>
            <div class="pt-2">
                <button class="btn rounded-md px-1" title="Add Member">
                    <i class="fas fa-user-plus"></i> Add Contributor
                </button>
            </div>
            {#if error}
                <div class="text-red-500 m-1 text-center">{error}</div>
            {/if}
        </div>
    {/if}
</BasePage>
<script>
    import projectService from "$lib/services/project.service.js";
    import {extractErr} from "$lib/helpers.js";
    import {page} from "$app/stores";
    import BasePage from "../../../../components/BasePage.svelte";
    import {onMount} from "svelte";
    import {setMenu} from "$lib/stores/menu.store.js";

    onMount(() => {
        setMenu('projects')
    })

    let errors = {}, error = '', loaders = 0;
    let items = []
    let project = null

    function fetchMembers() {
        loaders++
        projectService.fetchMembers($page.params.id).then(({data}) => {
            if (data.status === 200) items = data.data
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function fetchProject() {
        loaders++
        projectService.fetchItem($page.params.id).then(({data}) => {
            if (data.status === 200) project = data.data
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    function removeUser(item) {
        loaders++
        projectService.removeMembers($page.params.id, [item.id]).then(({data}) => {
            fetchMembers()
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }

    onMount(() => {
        fetchProject()
        fetchMembers()
    })
</script>
