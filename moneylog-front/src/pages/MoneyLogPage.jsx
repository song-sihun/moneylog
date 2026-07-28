import { useCallback, useEffect, useState } from 'react'
import Navbar from '../components/Navbar'
import DateRangeFilter from '../components/DateRangeFilter'
import TransactionForm from '../components/TransactionForm'
import TransactionList from '../components/TransactionList'
import Pagination from '../components/Pagination'
import {
  createTransaction,
  deleteTransaction,
  getTransactions,
  updateTransaction,
} from '../api/moneylog'
import { getDefaultDateRange, formatCurrency } from '../utils/date'

const PAGE_SIZE = 10

export default function MoneyLogPage() {
  const [dateRange, setDateRange] = useState(getDefaultDateRange())
  const [page, setPage] = useState(0)
  const [pageData, setPageData] = useState({
    content: [],
    totalPages: 0,
    totalElements: 0,
    number: 0,
  })
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const [formOpen, setFormOpen] = useState(false)
  const [editingTx, setEditingTx] = useState(null)

  const fetchTransactions = useCallback(async () => {
    setLoading(true)
    setError('')
    try {
      const data = await getTransactions({
        startDate: dateRange.startDate,
        endDate: dateRange.endDate,
        page,
        size: PAGE_SIZE,
        sort: 'date,desc',
      })
      setPageData(data)
    } catch (err) {
      setError(err.response?.data?.message || '거래 내역을 불러오지 못했습니다.')
    } finally {
      setLoading(false)
    }
  }, [dateRange, page])

  useEffect(() => {
    fetchTransactions()
  }, [fetchTransactions])

  const handleDateRangeChange = (range) => {
    setDateRange(range)
    setPage(0)
  }

  const openCreateForm = () => {
    setEditingTx(null)
    setFormOpen(true)
  }

  const openEditForm = (tx) => {
    setEditingTx(tx)
    setFormOpen(true)
  }

  const closeForm = () => {
    setFormOpen(false)
    setEditingTx(null)
  }

  const handleFormSubmit = async (payload) => {
    if (payload.id) {
      await updateTransaction(payload.id, payload)
    } else {
      await createTransaction(payload)
    }
    closeForm()
    fetchTransactions()
  }

  const handleDelete = async (tx) => {
    if (!window.confirm('이 거래 내역을 삭제하시겠습니까?')) return
    try {
      await deleteTransaction(tx.id)
      const isLastItemOnPage = pageData.content.length === 1 && page > 0
      if (isLastItemOnPage) {
        setPage((prev) => prev - 1)
      } else {
        fetchTransactions()
      }
    } catch (err) {
      alert(err.response?.data?.message || '삭제에 실패했습니다.')
    }
  }

  const totalIncome = pageData.content
    .filter((tx) => tx.type === 'INCOME')
    .reduce((sum, tx) => sum + tx.amount, 0)
  const totalExpense = pageData.content
    .filter((tx) => tx.type === 'EXPENSE')
    .reduce((sum, tx) => sum + tx.amount, 0)

  return (
    <div className="min-h-screen bg-slate-50">
      <Navbar />

      <main className="mx-auto max-w-3xl px-4 py-6">
        <div className="rounded-2xl bg-white p-5 shadow-sm ring-1 ring-slate-200">
          <div className="flex flex-wrap items-center justify-between gap-4">
            <DateRangeFilter
              startDate={dateRange.startDate}
              endDate={dateRange.endDate}
              onChange={handleDateRangeChange}
            />
            <button
              type="button"
              onClick={openCreateForm}
              className="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-indigo-700"
            >
              + 거래 등록
            </button>
          </div>

          <div className="mt-5 grid grid-cols-2 gap-3 sm:grid-cols-3">
            <div className="rounded-xl bg-blue-50 px-4 py-3">
              <p className="text-xs font-medium text-blue-500">이 페이지 수입</p>
              <p className="mt-1 text-lg font-bold text-blue-700">
                {formatCurrency(totalIncome)}원
              </p>
            </div>
            <div className="rounded-xl bg-red-50 px-4 py-3">
              <p className="text-xs font-medium text-red-500">이 페이지 지출</p>
              <p className="mt-1 text-lg font-bold text-red-700">
                {formatCurrency(totalExpense)}원
              </p>
            </div>
            <div className="col-span-2 rounded-xl bg-slate-100 px-4 py-3 sm:col-span-1">
              <p className="text-xs font-medium text-slate-500">총 {pageData.totalElements}건</p>
              <p className="mt-1 text-lg font-bold text-slate-700">
                {formatCurrency(totalIncome - totalExpense)}원
              </p>
            </div>
          </div>
        </div>

        <div className="mt-6 rounded-2xl bg-white p-5 shadow-sm ring-1 ring-slate-200">
          {error && (
            <p className="mb-3 rounded-lg bg-red-50 px-3 py-2 text-sm text-red-600">{error}</p>
          )}

          <TransactionList
            transactions={pageData.content}
            loading={loading}
            onEdit={openEditForm}
            onDelete={handleDelete}
          />

          <Pagination
            page={pageData.number}
            totalPages={pageData.totalPages}
            onPageChange={setPage}
          />
        </div>
      </main>

      <TransactionForm
        open={formOpen}
        initialData={editingTx}
        onClose={closeForm}
        onSubmit={handleFormSubmit}
      />
    </div>
  )
}
