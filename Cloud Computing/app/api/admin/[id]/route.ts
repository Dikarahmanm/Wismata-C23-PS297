import { NextResponse } from "next/server";
import { PrismaClient } from "@prisma/client";
import type { Profile } from "@prisma/client";
import { hash } from "bcrypt";

const prisma = new PrismaClient();

export const PATCH = async (request: Request, {params}: {params: {id: string}}) =>{
    const body: Profile = await request.json(); 

    const hashedpassword = await hash(body.password, 12);

    const userProfile = await prisma.profile.update({
        where:{
            id: Number(params.id)
        },
        data:{
            email: body.email,
            password: hashedpassword,
            name: body.name,
            nim: body.nim,
            picture: body.picture,
            userId: body.userId
        }
    });
    return NextResponse.json(userProfile, {status: 200});
}

export const DELETE = async (request: Request, {params}: {params : {id : string}}) => {
    const profile = await prisma.profile.delete({
        where : {
            id: Number(params.id)
        }
    });
    return NextResponse.json(profile, {status: 200});
}