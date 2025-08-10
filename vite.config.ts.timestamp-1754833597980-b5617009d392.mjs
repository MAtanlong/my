// vite.config.ts
import { defineConfig } from "file:///E:/%E4%B8%AA%E4%BA%BA/my/my/node_modules/.pnpm/vite@5.4.19_@types+node@22.17.0/node_modules/vite/dist/node/index.js";
import vue from "file:///E:/%E4%B8%AA%E4%BA%BA/my/my/node_modules/.pnpm/@vitejs+plugin-vue@5.2.4_vite@5.4.19_@types+node@22.17.0__vue@3.5.18_typescript@5.3.3_/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import path from "path";
import Inspector from "file:///E:/%E4%B8%AA%E4%BA%BA/my/my/node_modules/.pnpm/unplugin-vue-dev-locator@1.0.3_vite@5.4.19_@types+node@22.17.0_/node_modules/unplugin-vue-dev-locator/dist/vite.mjs";
import traeBadgePlugin from "file:///E:/%E4%B8%AA%E4%BA%BA/my/my/node_modules/.pnpm/vite-plugin-trae-solo-badge@1.0.0_vite@5.4.19_@types+node@22.17.0_/node_modules/vite-plugin-trae-solo-badge/dist/vite-plugin.esm.js";
var __vite_injected_original_dirname = "E:\\\u4E2A\u4EBA\\my\\my";
var vite_config_default = defineConfig({
  build: {
    sourcemap: "hidden"
  },
  plugins: [
    vue(),
    Inspector(),
    traeBadgePlugin({
      variant: "dark",
      position: "bottom-right",
      prodOnly: true,
      clickable: true,
      clickUrl: "https://www.trae.ai/solo?showJoin=1",
      autoTheme: true,
      autoThemeTarget: "#app"
    })
  ],
  resolve: {
    alias: {
      "@": path.resolve(__vite_injected_original_dirname, "./src")
      // ✅ 定义 @ = src
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJFOlxcXFxcdTRFMkFcdTRFQkFcXFxcbXlcXFxcbXlcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkU6XFxcXFx1NEUyQVx1NEVCQVxcXFxteVxcXFxteVxcXFx2aXRlLmNvbmZpZy50c1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRTovJUU0JUI4JUFBJUU0JUJBJUJBL215L215L3ZpdGUuY29uZmlnLnRzXCI7aW1wb3J0IHsgZGVmaW5lQ29uZmlnIH0gZnJvbSAndml0ZSdcclxuaW1wb3J0IHZ1ZSBmcm9tICdAdml0ZWpzL3BsdWdpbi12dWUnXHJcbmltcG9ydCBwYXRoIGZyb20gJ3BhdGgnXHJcbmltcG9ydCBJbnNwZWN0b3IgZnJvbSAndW5wbHVnaW4tdnVlLWRldi1sb2NhdG9yL3ZpdGUnXHJcbmltcG9ydCB0cmFlQmFkZ2VQbHVnaW4gZnJvbSAndml0ZS1wbHVnaW4tdHJhZS1zb2xvLWJhZGdlJ1xyXG5cclxuLy8gaHR0cHM6Ly92aXRlLmRldi9jb25maWcvXHJcbmV4cG9ydCBkZWZhdWx0IGRlZmluZUNvbmZpZyh7XHJcbiAgYnVpbGQ6IHtcclxuICAgIHNvdXJjZW1hcDogJ2hpZGRlbicsXHJcbiAgfSxcclxuICBwbHVnaW5zOiBbXHJcbiAgICB2dWUoKSxcclxuICAgIEluc3BlY3RvcigpLFxyXG4gICAgdHJhZUJhZGdlUGx1Z2luKHtcclxuICAgICAgdmFyaWFudDogJ2RhcmsnLFxyXG4gICAgICBwb3NpdGlvbjogJ2JvdHRvbS1yaWdodCcsXHJcbiAgICAgIHByb2RPbmx5OiB0cnVlLFxyXG4gICAgICBjbGlja2FibGU6IHRydWUsXHJcbiAgICAgIGNsaWNrVXJsOiAnaHR0cHM6Ly93d3cudHJhZS5haS9zb2xvP3Nob3dKb2luPTEnLFxyXG4gICAgICBhdXRvVGhlbWU6IHRydWUsXHJcbiAgICAgIGF1dG9UaGVtZVRhcmdldDogJyNhcHAnLFxyXG4gICAgfSksXHJcbiAgXSxcclxuICByZXNvbHZlOiB7XHJcbiAgICBhbGlhczoge1xyXG4gICAgICAnQCc6IHBhdGgucmVzb2x2ZShfX2Rpcm5hbWUsICcuL3NyYycpLCAvLyBcdTI3MDUgXHU1QjlBXHU0RTQ5IEAgPSBzcmNcclxuICAgIH0sXHJcbiAgfSxcclxufSlcclxuIl0sCiAgIm1hcHBpbmdzIjogIjtBQUEyTyxTQUFTLG9CQUFvQjtBQUN4USxPQUFPLFNBQVM7QUFDaEIsT0FBTyxVQUFVO0FBQ2pCLE9BQU8sZUFBZTtBQUN0QixPQUFPLHFCQUFxQjtBQUo1QixJQUFNLG1DQUFtQztBQU96QyxJQUFPLHNCQUFRLGFBQWE7QUFBQSxFQUMxQixPQUFPO0FBQUEsSUFDTCxXQUFXO0FBQUEsRUFDYjtBQUFBLEVBQ0EsU0FBUztBQUFBLElBQ1AsSUFBSTtBQUFBLElBQ0osVUFBVTtBQUFBLElBQ1YsZ0JBQWdCO0FBQUEsTUFDZCxTQUFTO0FBQUEsTUFDVCxVQUFVO0FBQUEsTUFDVixVQUFVO0FBQUEsTUFDVixXQUFXO0FBQUEsTUFDWCxVQUFVO0FBQUEsTUFDVixXQUFXO0FBQUEsTUFDWCxpQkFBaUI7QUFBQSxJQUNuQixDQUFDO0FBQUEsRUFDSDtBQUFBLEVBQ0EsU0FBUztBQUFBLElBQ1AsT0FBTztBQUFBLE1BQ0wsS0FBSyxLQUFLLFFBQVEsa0NBQVcsT0FBTztBQUFBO0FBQUEsSUFDdEM7QUFBQSxFQUNGO0FBQ0YsQ0FBQzsiLAogICJuYW1lcyI6IFtdCn0K
