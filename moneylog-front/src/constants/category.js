export const CATEGORY_OPTIONS = [
  { value: 'FOOD', label: '식비' },
  { value: 'TRANSPORT', label: '교통' },
  { value: 'SALARY', label: '급여' },
  { value: 'ETC', label: '기타' },
]

export const CATEGORY_LABELS = Object.fromEntries(
  CATEGORY_OPTIONS.map((opt) => [opt.value, opt.label]),
)
