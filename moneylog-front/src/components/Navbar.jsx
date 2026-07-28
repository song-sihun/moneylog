import { useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/login', { replace: true })
  }

  return (
    <header className="border-b border-slate-200 bg-white">
      <div className="mx-auto flex max-w-3xl items-center justify-between px-4 py-4">
        <h1 className="text-lg font-bold text-slate-900">MoneyLog</h1>
        <div className="flex items-center gap-3">
          {user?.nickname && (
            <span className="text-sm text-slate-500">
              <span className="font-medium text-slate-700">{user.nickname}</span>님
            </span>
          )}
          <button
            type="button"
            onClick={handleLogout}
            className="rounded-lg border border-slate-300 px-3 py-1.5 text-sm font-medium text-slate-600 hover:bg-slate-50"
          >
            로그아웃
          </button>
        </div>
      </div>
    </header>
  )
}
