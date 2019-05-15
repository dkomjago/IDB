import React, {useState,useCallback,useEffect} from 'react';
import DropZone from "./dropZone/DropZone"
import Navigation from "./navigation/Navigation";
import ModifyImage from "./modifyImage/ModifyImage";
import {uploadImage, getCurrentUser, addMod } from '../../../util/APIUtils';
import {notification} from "../../../util/Helpers";

export default function AddImage(props) {

    const [imgSrc, setImgSrc] = useState('');

    const [imgText, setImgText] = useState({
        upperText: '',
        lowerText: ''
    });

    const [cropped, setCropped] = useState({
        x: 0,
        y: 0,
        cropWidth: 0,
        cropHeight: 0,
        width: 0,
        height: 0
    });

    const [state, setState] = useState(0);

    useEffect( () => {
        if(props.img.src) {
            setImgSrc(props.img.src);
            setState(1);
        }
    },[props.img]);

    const onUpload = useCallback(src => {
        props.onCancel();
        setImgSrc(src);
        setState(state + 1);
    },[props,state]);

    const onCrop = useCallback((x, y, cropWidth, cropHeight, width, height) => {
        setCropped({
            x: x,
            y: y,
            cropWidth: cropWidth,
            cropHeight: cropHeight,
            width: width,
            height: height
        });
    },[]);

    const onAddText = useCallback((upperText, lowerText) => {
        setImgText({upperText, lowerText});
    },[]);

    function finish() {
        const mod = {
            cropX: cropped.x,
            cropY: cropped.y,
            cropWidth: cropped.cropWidth,
            cropHeight: cropped.cropHeight,
            width: cropped.width,
            height: cropped.height,
            upperText: imgText.upperText,
            lowerText: imgText.lowerText,
        };
        if (!props.img.id) {
            const uploadImageRequest = {
                image: imgSrc.split(',')[1],
                fileType: imgSrc.substring(5, 20).match(/\/((.*[a-z]){3}(?=;))/)[1],
                mod: mod,
                userId: null,
                username: null
            };
            getCurrentUser().then(response => {
                uploadImageRequest.userId = response.id;
                uploadImageRequest.username = response.username;
                uploadImage(uploadImageRequest).then(response => {
                    notification(response.message,response.success);
                    if(response.success && !response.exists) {
                        const image = new Image();
                        image.onload = function() {
                            props.onFinish({
                                src: imgSrc,
                                thumbnail: imgSrc,
                                thumbnailWidth: image.width,
                                thumbnailHeight: image.height,
                                realId: response.imageId,
                            })
                        };
                        image.src = imgSrc;
                    }
                });
                imgText.upperText = "";
                imgText.lowerText = "";
            });
        }
        else {
            addMod(props.img.id,mod).then(response => {
                notification(response.message,response.success);
            })
        }
        setState(0);
    }

    return (
        <div>
            <DropZone state={state} onUpload={onUpload}/>
            <Navigation state={state} onProceed={() => {
                state < 2 ? setState(state + 1) : finish();
            }} onCancel={() => {
                props.onCancel();
                setState(0);
            }}/>
            <ModifyImage state={state} onCrop={onCrop} onAddText={onAddText} imgSrc={imgSrc}/>
        </div>
    )
}