import React, {useState, useCallback} from 'react'
import ReactCrop from 'react-image-crop';
import 'react-image-crop/dist/ReactCrop.css';
import './Crop.css'
export default function Crop(props) {

    const [cropRect,setCropRect] = useState({
        x: 0,
        y: 0,
        width: 0,
        height: 0,
    });

    const onComplete = useCallback( (crop,pixelCrop) => {
        props.onCrop(crop,pixelCrop);
    },[props]);

    const onLoaded = useCallback( (crop,pixelCrop) => {
        props.onCrop(crop,pixelCrop);
    },[props]);

    if(props.state!==1){
        return null;
    }
    return (
        <div>
            <ReactCrop onChange={(crop) => {setCropRect(crop)}}
                   onComplete={onComplete} onImageLoaded={onLoaded}
                   crop={cropRect} src={props.src}/>
        </div>
    )
}
