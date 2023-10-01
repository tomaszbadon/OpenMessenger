import './Finder.sass'
function Finder(properties: any) {
    
    let onChange = properties.onChange as ((arg: string) => void);

    return <>
        <div className="finder">
            <input type="text" placeholder='Type a contact name here' onChange={(e) => onChange(e.target.value)} />
        </div>
    </>
}

export default Finder