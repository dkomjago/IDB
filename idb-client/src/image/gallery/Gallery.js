import React, {useCallback, useState, useEffect} from 'react';
import AddImage from "./addImage/AddImage";
import Showcase from "react-grid-gallery";
import {isEmpty, notification} from "../../util/Helpers";
import Mods from "./mods/Mods";
import {deleteImage, getImage, getImages} from "../../util/APIUtils";
import {Modal} from 'antd'

export default function Gallery() {

    const [gallery, setGallery] = useState([]);

    const [mods, setMods] = useState([]);

    const [state, setState] = useState(0);

    const [img, setImg] = useState({
        src: null,
        id: null
    });

    useEffect(() => {
        let isSubscribed = true;
        getImages()
            .then(response =>
                isSubscribed ?
                    setGallery(response) : null);
        return () => (isSubscribed = false);
    }, []);

    function setSource(index, image, browseMods) {
        const needSource = image.src === "";
        getImage(image.realId, needSource).then(response => {
            if(browseMods) {
                if (!isEmpty(response.mods)) {
                    setMods(response.mods);
                    setState(1);
                }
                else {
                    notification("This image has no modifications!", false);
                    return;
                }
            }
            if (needSource) {
                image.src = response.image;
            }
            setImg({
                src: image.src,
                id: image.realId
            });
        });
    }

    const onClickThumbnail = useCallback((index) => {
        setSource(index, gallery[index], true);
    },[gallery]);

    const onSelectImage = useCallback((index,image) => {
        setSource(index, image, false);
    }, []);

    function cancel() {
        setImg({
            src: null,
            id: null
        })
    }

    function add(image) {
        setGallery(gallery.concat(image));
    }

    const onDeleteImage = useCallback((index,image) => {
        Modal.confirm({
            title: 'Are you sure you want to delete this item?',
            content: 'This operation with also delete all modifications of this image',
            okText: 'Yes',
            okType: 'danger',
            cancelText: 'No',
            onOk() {
                deleteImage(image.realId).then(response => {
                    notification(response.message,response.success);
                    cancel();
                    setGallery(gallery.filter((x,i)=>i!==index));
                })
            },
            onCancel() {},
        });
    },[gallery]);

    return (
        <div>
            {
                state === 0 ?
                    <div>
                        <AddImage img={img} onCancel={cancel} onFinish={add}/>
                        <br/>
                        <Showcase images={gallery} onClickThumbnail={onClickThumbnail}
                                  onSelectImage={onSelectImage} onDeleteImage={onDeleteImage}/>
                        {isEmpty(gallery) ? "Gallery is empty :(" : null}
                    </div> :
                    <Mods src={img.src} mods={mods} setMods={setMods} onReturn={() => {
                        setState(0);
                        cancel();
                    }}/>
            }
        </div>
    )
}