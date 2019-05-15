import React, {useEffect, useCallback, useRef, useState} from "react";
import {Button, Modal, Row} from 'antd';
import { drawOutlineText } from "../addImage/modifyImage/addText/fonts/WBOtext";
import {isEmpty, notification} from "../../../util/Helpers";
import {deleteMod} from "../../../util/APIUtils";

export default function Mods(props) {

    const canvas = useRef();

    const [modIndex, setModIndex] = useState(0);

    useEffect(() => {
        if (!isEmpty(props.mods)) {
            const ctx = canvas.current.getContext("2d");
            let image = new Image();
            image.onload = function () {
                let mod = props.mods[modIndex];
                ctx.drawImage(
                    image,
                    mod.cropX,
                    mod.cropY,
                    mod.cropWidth === 0? image.width - mod.cropX : mod.cropWidth,
                    mod.cropHeight === 0? image.height - mod.cropY : mod.cropHeight,
                    0,
                    0,
                    mod.width,
                    mod.height
                );
                drawOutlineText(true, canvas.current, 'Impact', mod.upperText.toUpperCase());
                drawOutlineText(false, canvas.current, 'Impact', mod.lowerText.toUpperCase());
            };
            image.src = props.src;
        }
    },[modIndex,props]);

    function save() {
        const link = document.createElement('a');
        link.href = canvas.current.toDataURL("image/png");
        link.download = 'img';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }

    const remove = useCallback(()=> {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'Operation is irreversible.',
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',
            onOk() {
                deleteMod(props.mods[modIndex].id).then(response => {
                    notification(response.message, response.success);
                });
                if (mods.length === 1) {
                    if (modIndex === mods.length-1) setModIndex(modIndex - 1);
                }
                else props.onReturn();
                const mods = props.mods.filter((x, i) => i !== modIndex);
                props.setMods(mods);
            },
            onCancel() {},
        });
    },[modIndex, props]);

    return (
        <div>
            <Row type="flex" justify="center" >
                    <Button type="dashed"  icon="double-left" size="large" onClick={() => {
                        if (modIndex > 0) setModIndex(modIndex - 1);
                    }}/>
                    <Button type="dashed" size="large" onClick={props.onReturn}>Back</Button>
                    <Button type="dashed" size="large" onClick={remove}>Delete</Button>
                    <Button type="dashed" size="large" onClick={save}>Save Image</Button>
                    <Button type="dashed" size="large" icon="double-right" onClick={() => {
                        if (modIndex < props.mods.length - 1) setModIndex(modIndex + 1);
                    }}/>
            </Row>
            <Row justify="center" >
                <p align="center">
                    <canvas ref={canvas} width={props.mods[modIndex].width} height={props.mods[modIndex].height}/>
                </p>
            </Row>
        </div>
    )
}
