<BasePage title="Project Members">
    {#if project}
        <div class="card">
            <div class="pb-1 font-bold">
                Project Members :: <a href="/projects/{project.id}">{project.projectName}</a>
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
                    {#if project && project.ownerId === $user.id}
                        <th>Actions</th>
                    {/if}
                </tr>
                </thead>
                <tbody>
                {#each items as item,i(item.id)}
                    <tr class="text-center">
                        <td>{i + 1}</td>
                        <td>{item.id}</td>
                        <td class="text-left">{item.name}</td>
                        <td class="text-left">{item.email}</td>
                        {#if project && project.ownerId === $user.id}
                            <td>
                                <i class="fas fa-minus-circle text-red-500 cursor-pointer"
                                   on:click={() => removeUser(item)}
                                   title="Remove Member"></i>
                            </td>
                        {/if}
                    </tr>
                {/each}
                {#if !items.length}
                    <tr>
                        <td colspan="8" class="p-2 text-black bg-yellow-100 text-center">No Data Available</td>
                    </tr>
                {/if}
                </tbody>
            </table>
            {#if project.ownerId === $user.id}
                <form class="border rounded-md mt-2 p-2 flex justify-end" on:submit|preventDefault={addMember}>
                    <div class="text-input">
                        <label for="email">Email Address</label>
                        <input type="email" name="email" id="email" class="text-field" bind:value={emailToAdd} required>
                    </div>
                    <button class="btn ml-2" type="submit">
                        <i class="fas fa-user-plus"></i> Add
                    </button>
                </form>
            {/if}
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
    import BasePage from "../../../../lib/components/BasePage.svelte";
    import {onMount} from "svelte";
    import {setMenu} from "$lib/stores/menu.store.js";
    import {user} from "$lib/stores/user.store.js";

    onMount(() => {
        setMenu('projects')
    })

    let errors = {}, error = '', loaders = 0;
    let items = []
    let project = null
    let emailToAdd = ''

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
        if (!confirm("Are you sure to remove " + item.name + ' ?')) return;
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

    function addMember() {
        loaders++
        projectService.addMembers($page.params.id, [emailToAdd]).then(({data}) => {
            alert('Member added successfully')
            fetchMembers()
        }).catch(err => {
            [error, errors] = extractErr(err)
        }).finally(() => loaders--)
    }
</script>
