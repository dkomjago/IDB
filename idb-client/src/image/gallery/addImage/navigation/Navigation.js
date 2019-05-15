import React from 'react';
import {Button} from 'antd';

export default function Navigation(props) {

    const ButtonGroup = Button.Group;

    if (props.state === 0) {
        return null;
    }
        return (
            <div>
                <ButtonGroup>
                    <Button onClick={props.onCancel} htmlType='button' type="primary">Cancel</Button>
                    <Button onClick={props.onProceed} htmlType='button' type="primary">Next</Button>
                </ButtonGroup>
            </div>
        );
}