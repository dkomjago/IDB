import React, {useCallback, useMemo} from 'react'
import {useDropzone} from 'react-dropzone'
import {FaFileImage} from "react-icons/fa";
import './DropZone.css'

const baseStyle = {
    flex: 1,
    borderWidth: 2,
    borderColor: '#666',
    borderStyle: 'dashed',
    borderRadius: 5,
};

const activeStyle = {
    borderStyle: 'solid',
    borderColor: '#6c6',
    backgroundColor: '#eee'
};

const acceptStyle = {
    borderStyle: 'solid',
    borderColor: '#00e676'
};

const rejectStyle = {
    borderStyle: 'solid',
    borderColor: '#ff1744'
};

export default function DropZone(props) {

    const onDrop = useCallback(acceptedFiles => {
        const reader = new FileReader();

        reader.onabort = () => console.log('file reading was aborted');
        reader.onerror = () => console.log('file reading has failed');
        reader.onload = () => {
            // read uploaded file as dataurl and pass to parent
            const readUrl = reader.result;
            props.onUpload(readUrl);
        };
        reader.readAsDataURL(acceptedFiles[0]);
    }, []);

    const {getRootProps, getInputProps, isDragActive, isDragAccept,
        isDragReject} = useDropzone({
        accept: 'image/jpeg, image/png, image/gif, image/tiff',
        multiple: false,
        onDrop
        });

    const style = useMemo(() => ({
        ...baseStyle,
        ...(isDragActive ? activeStyle : {}),
        ...(isDragAccept ? acceptStyle : {}),
        ...(isDragReject ? rejectStyle : {})
    }), [
        isDragActive,
        isDragReject
    ]);

    if(props.state!==0) {
        return null
    }
    return (
        <div {...getRootProps({style})}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p className="drop-text">Drop the image here ... <br/><FaFileImage/></p> :
                    <p className="drop-text">Drag 'n' drop some files here, or click to select files <br/><FaFileImage/></p>
            }
        </div>
    )
}