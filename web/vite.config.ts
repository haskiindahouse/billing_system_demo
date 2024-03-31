import { defineConfig } from "vitest/config"
import react from "@vitejs/plugin-react"
import path from "path"

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      src: path.resolve(__dirname, "./src"),
    },
  },
  plugins: [react()],
  server: {
    port: 3000,
    host: true
  },
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "src/setupTests",
    mockReset: true,
  },
})
