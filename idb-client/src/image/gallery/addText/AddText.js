import {useCallback, useRef} from "react";
import { Input } from 'antd';
import React from "react";

export default function AddText(props) {

    const upperInput = useRef();
    const lowerInput = useRef();

    const onChange = useCallback(()=>{
        props.onAddText(upperInput.current.input.value,lowerInput.current.input.value)
    });

    if(props.state!==2) {
        return null
    }
    return (
        <div>
            <Input {...props} ref={upperInput} placeholder='Upper text' onChange={onChange}/>
            <Input {...props} ref={lowerInput} placeholder='Lower text' onChange={onChange}/>
        </div>
    )
}
