<aside class="h-full flex flex-col">
    <ul class="flex-grow overflow-auto flex flex-col menu" class:open={isOpen}>
        <li class="menu-item" class:active={isMenu('home')}>
            <a href="/"><i class="fas fa-home"></i><span class="menu-title">Home</span></a>
        </li>
        <li class="menu-item" class:active={isMenu('projects')}>
            <a href="/projects"><i class="fas fa-code"></i><span class="menu-title">Projects</span></a>
        </li>
        <li class="menu-item" class:active={isMenu('users')}>
            <a href="/users"><i class="fas fa-user"></i><span class="menu-title">Users</span></a>
        </li>
        <li class="menu-item" class:active={isMenu('about')}>
            <a href="/about"><i class="fas fa-info-circle"></i><span class="menu-title">About</span></a>
        </li>
        <li class="menu-item" class:active={isMenu('contact')}>
            <a href="/contact"><i class="fas fa-comment"></i><span class="menu-title">Contact</span></a>
        </li>
        <li class="border-b" class:invisible={!isOpen}></li>
        <!--<li class="flex-grow"></li>-->
        <li class="menu-item">
            <a href="/" role="button" on:click|preventDefault={logout}>
                <i class="fas fa-sign-out"></i>
                <span class="menu-title">Logout</span>
            </a>
        </li>
    </ul>
    <button class="menu-toggle h-8 bg-gray-600 text-white text-center px-2 active:opacity-80" on:click={toggleMenu}>
        {#if isOpen}
            <i class="fas fa-arrow-left"></i>
        {:else}
            <i class="fas fa-arrow-right"></i>
        {/if}
    </button>
</aside>
<script>
    import {menu} from "$lib/stores/menu.store.js";
    import {onMount} from "svelte";
    import {goto} from "$app/navigation";
    import authService from "$lib/services/auth.service.js";

    let isOpen = false;

    $: isMenu = function(id, level = 1) {
        return $menu['level' + level] === id;
    }

    function toggleMenu() {
        isOpen = !isOpen
        window.localStorage.setItem('isMenuOpen', isOpen)
    }

    onMount(() => {
        isOpen = window.localStorage.getItem('isMenuOpen') === 'true';
    })

    function logout() {
        if(!confirm('Are you sure to logout ?')) return;
        authService.logout()
    }
</script>
