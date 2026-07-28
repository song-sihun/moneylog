// Date를 'YYYY-MM-DD' 문자열로 변환 (로컬 타임존 기준, toISOString의 UTC 변환 오차 방지)
export function toDateString(date) {
  const y = date.getFullYear()
  const m = String(date.getMonth() + 1).padStart(2, '0')
  const d = String(date.getDate()).padStart(2, '0')
  return `${y}-${m}-${d}`
}

export function getDefaultDateRange() {
  const today = new Date()
  const firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1)
  return {
    startDate: toDateString(firstDayOfMonth),
    endDate: toDateString(today),
  }
}

export function formatCurrency(amount) {
  return new Intl.NumberFormat('ko-KR').format(amount)
}
