//Draw impact color white/ black outline to canvas

export function drawOutlineText(upper, canvas, fontName, text) {
    const ctx = canvas.getContext("2d");
    ctx.textBaseline = upper? "top" : "bottom";
    const y = upper? 0: canvas.height;
    ctx.textAlign = "center";
    let fontSize=100;
    do{
        fontSize--;
        ctx.font=fontSize+"px "+fontName;
    }while(ctx.measureText(text).width>canvas.width);
    ctx.strokeStyle = 'black';
    ctx.lineWidth = 5*fontSize/100;
    ctx.strokeText(text, canvas.width/2, y);
    ctx.fillStyle = 'white';
    ctx.fillText(text, canvas.width/2, y);
}