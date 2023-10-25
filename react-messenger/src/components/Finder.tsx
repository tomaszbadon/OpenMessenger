import './Finder.sass'

type Callback =  ((arg: string) => void)

interface FinderProp {
    onChange: Callback
}

function Finder(prop: FinderProp) {
    
    const onChange = prop.onChange

    return <>
        <div className="finder">
            <input type="text" placeholder='Type a contact name here' onChange={(e) => onChange(e.target.value)} />
        </div>
    </>
}

export default Finder