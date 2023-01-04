import adapter from '@sveltejs/adapter-static';
import { vitePreprocess } from '@sveltejs/kit/vite';

/** @type {import('@sveltejs/kit').Config} */
const config = {
	kit: {
		adapter: adapter({
			assets: '../src/main/resources/public',
			pages: '../src/main/resources/public',
			fallback: 'index.html',
		}),
	},
	preprocess: vitePreprocess(),
};

export default config;
