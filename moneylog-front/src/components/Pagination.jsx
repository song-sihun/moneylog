export default function Pagination({ page, totalPages, onPageChange }) {
  if (totalPages <= 0) return null

  return (
    <div className="flex items-center justify-center gap-3 pt-4">
      <button
        type="button"
        disabled={page <= 0}
        onClick={() => onPageChange(page - 1)}
        className="rounded-lg border border-slate-300 px-3 py-1.5 text-sm font-medium text-slate-600 transition hover:bg-slate-50 disabled:cursor-not-allowed disabled:opacity-40"
      >
        이전
      </button>
      <span className="text-sm text-slate-500">
        {page + 1} / {totalPages}
      </span>
      <button
        type="button"
        disabled={page >= totalPages - 1}
        onClick={() => onPageChange(page + 1)}
        className="rounded-lg border border-slate-300 px-3 py-1.5 text-sm font-medium text-slate-600 transition hover:bg-slate-50 disabled:cursor-not-allowed disabled:opacity-40"
      >
        다음
      </button>
    </div>
  )
}
