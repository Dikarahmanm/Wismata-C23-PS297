import { NextResponse } from "next/server";
import { PrismaClient } from "@prisma/client";
import type { Profile } from "@prisma/client";
import { hash } from "bcrypt";

const prisma = new PrismaClient();

export const POST = async (request: Request) => {
    const body: Profile = await request.json();

    const hashedpassword = await hash(body.password, 12);

    const profile = await prisma.profile.create({
        data : {
            email: body.email,
            password: hashedpassword,
            name: body.name,
            nim: body.nim,
            picture: body.picture,
            userId: body.userId
        }
    });
    return NextResponse.json(profile,{status: 201});
}