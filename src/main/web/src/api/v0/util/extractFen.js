export default function extractFen(body) {
    const slimFen = body.split(" ")[0];
    return slimFen;
}