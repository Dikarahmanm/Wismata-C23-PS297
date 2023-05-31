import { NextResponse } from "next/server";
import { PrismaClient } from "@prisma/client";
import type { Content } from "@prisma/client";
const prisma = new PrismaClient();

export const PATCH = async (request: Request, {params}: {params: {id: string}}) =>{
    const body: Content = await request.json();
    const product = await prisma.content.update({
        where:{
            id: Number(params.id)
        },
        data:{
            author: body.author,
            title: body.title,
            subtitle: body.subtitle,
            image: body.image,
            articleId: body.articleId
        }
    });
    return NextResponse.json(product, {status: 200});
}

export const DELETE = async (request: Request, {params}: {params : {id : string}}) => {
    const content = await prisma.content.delete({
        where : {
            id: Number(params.id)
        }
    });
    return NextResponse.json(content, {status: 200});
}