import { formatCurrency } from '../utils/date'
import { CATEGORY_LABELS } from '../constants/category'

export default function TransactionList({ transactions, loading, onEdit, onDelete }) {
  if (loading) {
    return (
      <div className="flex items-center justify-center py-16 text-sm text-slate-400">
        불러오는 중...
      </div>
    )
  }

  if (transactions.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center gap-1 py-16 text-slate-400">
        <p className="text-sm">해당 기간의 거래 내역이 없습니다.</p>
      </div>
    )
  }

  return (
    <ul className="divide-y divide-slate-100">
      {transactions.map((tx) => {
        const isIncome = tx.type === 'INCOME'
        return (
          <li
            key={tx.id}
            className="flex items-center justify-between gap-4 px-1 py-3 sm:px-2"
          >
            <div className="flex min-w-0 items-center gap-3">
              <span
                className={`shrink-0 rounded-full px-2 py-1 text-xs font-semibold ${
                  isIncome ? 'bg-blue-50 text-blue-600' : 'bg-red-50 text-red-600'
                }`}
              >
                {isIncome ? '수입' : '지출'}
              </span>
              <div className="min-w-0">
                <p className="truncate text-sm font-medium text-slate-800">
                  {CATEGORY_LABELS[tx.category] || tx.category}
                  {tx.description && (
                    <span className="ml-2 truncate text-xs font-normal text-slate-400">
                      {tx.description}
                    </span>
                  )}
                </p>
                <p className="text-xs text-slate-400">{tx.date}</p>
              </div>
            </div>

            <div className="flex shrink-0 items-center gap-3">
              <span
                className={`text-sm font-semibold ${
                  isIncome ? 'text-blue-600' : 'text-red-600'
                }`}
              >
                {isIncome ? '+' : '-'}
                {formatCurrency(tx.amount)}원
              </span>
              <div className="flex gap-1">
                <button
                  type="button"
                  onClick={() => onEdit(tx)}
                  className="rounded-md px-2 py-1 text-xs font-medium text-slate-500 hover:bg-slate-100"
                >
                  수정
                </button>
                <button
                  type="button"
                  onClick={() => onDelete(tx)}
                  className="rounded-md px-2 py-1 text-xs font-medium text-red-500 hover:bg-red-50"
                >
                  삭제
                </button>
              </div>
            </div>
          </li>
        )
      })}
    </ul>
  )
}
