import React, {useState,useCallback} from 'react';
import DropZone from './dropZone/DropZone';
import Navigation from "./navigation/Navigation";
import ModifyImage from "./modifyImage/ModifyImage";
import { uploadImage, getCurrentUser } from '../../util/APIUtils';

export default function Gallery() {

    const [imgSrc,setImgSrc] = useState('');
    const [imgText,setImgText] = useState({
        upperText: '',
        lowerText: ''
    });

    const [cropped,setCropped] = useState({
        x: 0,
        y: 0,
        cropWidth: 0,
        cropHeight: 0,
        width: 0,
        height: 0
    });

    const [state,setState] = useState(0);

    const onUpload = useCallback(src => {
        setImgSrc(src);
        setState(state+1);
    });

    const onCrop = useCallback((x,y,cropWidth,cropHeight,width,height) => {
        setCropped({
            x: x,
            y: y,
            cropWidth: cropWidth,
            cropHeight: cropHeight,
            width: width,
            height: height});
    });

    const onAddText = useCallback((upperText, lowerText) => {
        setImgText({upperText,lowerText});
    });

    const onFinish = useCallback(() => {
        console.log(imgSrc);
        const uploadImageRequest = {
            image: '',
            fileType: imgSrc.match(/\/((.*[a-z]){3}(?=;))/)[1],
            mods: [cropped.x, cropped.y, cropped.cropWidth, cropped.cropHeight, cropped.width, cropped.height,imgText.upperText,imgText.lowerText],
            userId: null,
            username: null
        };
        console.log(uploadImageRequest.image);
        getCurrentUser().then(response => {
            uploadImageRequest.userId = response.id;
            uploadImageRequest.username = response.username;
            console.log(uploadImageRequest);
            uploadImage(uploadImageRequest)
        });
        setState(0);
    });

    return(
        <div>
            <DropZone state={state} onUpload={onUpload}/>
            <Navigation state={state} onProceed={() => {state<2?setState(state+1):onFinish();}} onCancel={() => setState(0)}/>
            <ModifyImage state={state} onCrop={onCrop} onAddText={onAddText} imgSrc={imgSrc}/>
        </div>
    )
}