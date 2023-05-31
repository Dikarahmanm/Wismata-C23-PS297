import { PrismaClient } from "@prisma/client";
import AddProfile from "./addProfile";
import DeleteProfile from "./deleteProfile";
import UpdateProfile from "./updateProfile";

const prisma = new PrismaClient();

const getProfiles = async () => {
    const res = await prisma.profile.findMany({
        select : {
            id : true,
            email : true,
            password: true,
            name : true,
            nim : true,
            picture : true,
            userId : true,
            user : true,
        },
    });
    return res;
};

const getUsers = async () => {
    const res = await prisma.user.findMany();
    return res;
}

const Profile = async () => {
    const [profiles, users] = await Promise.all([
        getProfiles(),
        getUsers()
    ]);

    return (
    <div>
        <div className="mb-2">
            <AddProfile users={users}/>
        </div>
        <table className=" table w-full">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>NIM</th>
                    <th>Profile Pict</th>
                    <th>Role</th>
                    <th className="text-center">Action</th>
                </tr>
            </thead>
            <tbody>
            {profiles.map((profile, index) => (
                <tr key = { profile.id }>
                    <td> { index + 1 } </td>
                    <td> { profile.email } </td>
                    <td> { profile.name } </td>
                    <td> { profile.nim } </td>
                    <td> { profile.picture } </td>
                    <td> { profile.user.role }</td>
                    <td className="flex justify-center space-x-1">
                        <UpdateProfile users={users} profile={profile}/>
                        <DeleteProfile profile={profile}/>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    </div>
  )
}

export default Profile