import {useCallback, useRef} from "react";
import { Input } from 'antd';
import React from "react";

export default function AddText(props) {

    const upperInput = useRef();
    const lowerInput = useRef();

    const onChange = useCallback(()=>{
        props.onAddText(upperInput.current.input.value, lowerInput.current.input.value)
    },[props]);

    if(props.state!==2) {
        return null
    }
    return (
        <div>
            <Input ref={upperInput} placeholder='Upper text' defaultValue="" onChange={onChange}/>
            <Input ref={lowerInput} placeholder='Lower text' defaultValue="" onChange={onChange}/>
        </div>
    )
}
