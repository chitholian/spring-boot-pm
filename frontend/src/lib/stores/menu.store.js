import {writable} from "svelte/store";

export const menu = writable({
    level1: null,
    level2: null,
    // ...
})

export function setMenu(id, level = 1) {
    menu.update(i => {
        i['level' + level] = id;
        return i;
    })
}
