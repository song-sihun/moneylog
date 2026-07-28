export default function DateRangeFilter({ startDate, endDate, onChange }) {
  return (
    <div className="flex flex-wrap items-end gap-3">
      <div>
        <label className="mb-1 block text-xs font-medium text-slate-500">시작일</label>
        <input
          type="date"
          value={startDate}
          max={endDate}
          onChange={(e) => onChange({ startDate: e.target.value, endDate })}
          className="rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-100"
        />
      </div>
      <span className="pb-2 text-slate-400">~</span>
      <div>
        <label className="mb-1 block text-xs font-medium text-slate-500">종료일</label>
        <input
          type="date"
          value={endDate}
          min={startDate}
          onChange={(e) => onChange({ startDate, endDate: e.target.value })}
          className="rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-100"
        />
      </div>
    </div>
  )
}
