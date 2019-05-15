import React, {useState, useCallback, useRef} from 'react'
import 'react-image-crop/dist/ReactCrop.css';
import './ModifyImage.css'
import Crop from "./crop/Crop";
import AddText from "./addText/AddText";
import {drawOutlineText} from "./addText/fonts/WBOtext";

export default function ModifyImage(props) {

    const [aspect,setAspect] = useState(1);
    const [croppedImage,setCroppedImage] = useState('');

    const canvas = useRef();

    const onCrop = useCallback( (crop,pixelCrop) => {
        const ctx = canvas.current.getContext("2d");
        let image = new Image();
        image.onload = function() {
            const aspectTest = pixelCrop.width/pixelCrop.height;
            const aspectIsFaulty = isNaN(aspectTest) || aspectTest===0;

            const aspect = aspectIsFaulty? image.width / image.height : aspectTest;
            const x = aspectIsFaulty? 0 :  pixelCrop.x;
            const y = aspectIsFaulty? 0 :  pixelCrop.y;
            setAspect(aspect);

            let width = 700 * aspect;
            let height = 700;
            if (aspect > 1) {
                width = 700;
                height = 700 / aspect;
            }

            aspectIsFaulty?
                ctx.drawImage(image,x,y,width,height):
                ctx.drawImage(image,x, y, pixelCrop.width, pixelCrop.height, 0, 0, width, height);

            setCroppedImage(canvas.current.toDataURL('image/jpg'));
            props.onCrop(x,y,pixelCrop.width,pixelCrop.height,width,height);
        };
        image.src = props.imgSrc;
    },[props]);

    const onAddText = useCallback( (upperText,lowerText)=> {
        const ctx = canvas.current.getContext("2d");
        let image = new Image();
        image.onload = function() {
            ctx.drawImage(image,0,0,canvas.current.width,canvas.current.height);
            drawOutlineText(true, canvas.current,'Impact', upperText.toUpperCase());
            drawOutlineText(false, canvas.current,'Impact', lowerText.toUpperCase());
            props.onAddText(upperText,lowerText);
        };
        image.src = croppedImage;
    },[props,croppedImage]);

    if (props.state < 1) {
        return null;
    }
    return (
        <div>
            <AddText state={props.state} onAddText={onAddText} />
            {
                props.state ===1?
                    aspect > 1 ?
                    <canvas className="hidden-canvas" ref={canvas} width='700'
                            height={700 / aspect}/> :
                    <canvas className="hidden-canvas" ref={canvas} width={700 * aspect}
                            height='700'/>
                    :
                    aspect > 1 ?
                    <canvas className="canvas" ref={canvas} width='700'
                            height={700 / aspect}/> :
                    <canvas className="canvas" ref={canvas} width={700 * aspect}
                    height='700'/>

            }
            <Crop state={props.state} onCrop={onCrop} src={props.imgSrc} />
        </div>
    )
}
