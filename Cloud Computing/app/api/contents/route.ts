import { NextResponse } from "next/server";
import { PrismaClient } from "@prisma/client";
import type { Content } from "@prisma/client";
const prisma = new PrismaClient();

export const POST = async (request: Request) => {
    const body: Content = await request.json();
    const content = await prisma.content.create({
        data : {
            author: body.author,
            title: body.title,
            subtitle: body.subtitle,
            image: body.image,
            articleId: body.articleId
        }
    });
    return NextResponse.json(content,{status: 201});
}