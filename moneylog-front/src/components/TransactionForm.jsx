import { useEffect, useState } from 'react'
import { toDateString } from '../utils/date'
import { CATEGORY_OPTIONS } from '../constants/category'

const EMPTY_FORM = {
  type: 'EXPENSE',
  amount: '',
  category: CATEGORY_OPTIONS[0].value,
  description: '',
  date: toDateString(new Date()),
}

export default function TransactionForm({ open, initialData, onClose, onSubmit }) {
  const [form, setForm] = useState(EMPTY_FORM)
  const [error, setError] = useState('')
  const [submitting, setSubmitting] = useState(false)

  const isEdit = !!initialData

  useEffect(() => {
    if (!open) return
    setError('')
    setForm(
      initialData
        ? {
            type: initialData.type,
            amount: initialData.amount,
            category: initialData.category,
            description: initialData.description,
            date: initialData.date,
          }
        : EMPTY_FORM,
    )
  }, [open, initialData])

  if (!open) return null

  const handleChange = (e) => {
    const { name, value } = e.target
    setForm((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')

    const amountNumber = Number(form.amount)
    if (!form.amount || Number.isNaN(amountNumber) || amountNumber <= 0) {
      setError('금액을 올바르게 입력해주세요.')
      return
    }
    if (!form.category) {
      setError('카테고리를 선택해주세요.')
      return
    }

    setSubmitting(true)
    try {
      await onSubmit({
        ...(isEdit ? { id: initialData.id } : {}),
        type: form.type,
        amount: amountNumber,
        category: form.category,
        description: form.description.trim(),
        date: form.date,
      })
    } catch (err) {
      setError(err.response?.data?.message || '저장에 실패했습니다.')
      setSubmitting(false)
      return
    }
    setSubmitting(false)
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/40 px-4">
      <div className="w-full max-w-md rounded-2xl bg-white p-6 shadow-xl">
        <div className="mb-5 flex items-center justify-between">
          <h2 className="text-lg font-semibold text-slate-900">
            {isEdit ? '거래 내역 수정' : '거래 내역 등록'}
          </h2>
          <button
            type="button"
            onClick={onClose}
            className="rounded-full p-1 text-slate-400 hover:bg-slate-100 hover:text-slate-600"
            aria-label="닫기"
          >
            ✕
          </button>
        </div>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-2 gap-2">
            <button
              type="button"
              onClick={() => setForm((prev) => ({ ...prev, type: 'EXPENSE' }))}
              className={`rounded-lg border py-2 text-sm font-medium transition ${
                form.type === 'EXPENSE'
                  ? 'border-red-500 bg-red-50 text-red-600'
                  : 'border-slate-300 text-slate-500 hover:bg-slate-50'
              }`}
            >
              지출
            </button>
            <button
              type="button"
              onClick={() => setForm((prev) => ({ ...prev, type: 'INCOME' }))}
              className={`rounded-lg border py-2 text-sm font-medium transition ${
                form.type === 'INCOME'
                  ? 'border-blue-500 bg-blue-50 text-blue-600'
                  : 'border-slate-300 text-slate-500 hover:bg-slate-50'
              }`}
            >
              수입
            </button>
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-slate-700">금액</label>
            <input
              type="number"
              name="amount"
              min="0"
              value={form.amount}
              onChange={handleChange}
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-100"
              placeholder="10000"
            />
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-slate-700">카테고리</label>
            <select
              name="category"
              value={form.category}
              onChange={handleChange}
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-100"
            >
              {CATEGORY_OPTIONS.map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-slate-700">날짜</label>
            <input
              type="date"
              name="date"
              value={form.date}
              onChange={handleChange}
              className="w-full rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-100"
            />
          </div>

          <div>
            <label className="mb-1 block text-sm font-medium text-slate-700">메모</label>
            <textarea
              name="description"
              rows={2}
              value={form.description}
              onChange={handleChange}
              className="w-full resize-none rounded-lg border border-slate-300 px-3 py-2 text-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-100"
              placeholder="메모 (선택)"
            />
          </div>

          {error && <p className="text-sm text-red-600">{error}</p>}

          <div className="flex gap-2 pt-2">
            <button
              type="button"
              onClick={onClose}
              className="flex-1 rounded-lg border border-slate-300 py-2.5 text-sm font-semibold text-slate-600 hover:bg-slate-50"
            >
              취소
            </button>
            <button
              type="submit"
              disabled={submitting}
              className="flex-1 rounded-lg bg-indigo-600 py-2.5 text-sm font-semibold text-white transition hover:bg-indigo-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
              {submitting ? '저장 중...' : '저장'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
