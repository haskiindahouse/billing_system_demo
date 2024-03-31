import { Outlet } from "react-router-dom"
import { Navbar } from "src/components/Navbar"
import { Sidebar } from "src/components/Sidebar"

export const Layout = () => (
  <div className="relative">
    <Navbar />
    <div className="flex">
      <Sidebar />
      <Outlet />
    </div>
  </div>
)
