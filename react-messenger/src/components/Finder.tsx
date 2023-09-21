import './Finder.sass'
import { memo } from 'react';

function Finder(properties: any) {
    
    let onChange = properties.onChange as ((arg: string) => void);

    return <>
        <div className="finder">
            <input type="text" onChange={(e) => onChange(e.target.value)} />
        </div>
    </>
}

export default memo(Finder);