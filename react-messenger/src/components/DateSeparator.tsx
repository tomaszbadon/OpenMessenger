import './DateSeparator.sass'

interface DateSeparatorProp { condition: boolean, date: string }

export function DateSeparator(prop: DateSeparatorProp) {
  if (prop.condition) {
    return (
      <div className='date-separator'>
        - {prop.date} -
      </div>
    )
  } else {
    return <></>
  }
}